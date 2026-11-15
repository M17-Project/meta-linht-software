#!/bin/sh
set -e

echo "Disable services during setup..."
systemctl disable linht-zmq-proxy.service --now
systemctl disable linht-volume-ctrl.service --now
systemctl disable linht-gui-test.service --now

echo "Running first-boot script..."
fbi -d /dev/fb0 -a -t 2 -noverbose /usr/share/linht/images/preparing.png < /dev/null >&/dev/null 2>&1
echo "Building GRC files..."
cd /usr/share/linht/grc
grcc /usr/share/linht/grc/som_m17_ptt.grc

echo "Setting up audio..."
#
# TLV320AIC3100. Only the mono class-D speaker output and the electret on
# MIC1RP are wired; the headphone driver is unused and kept powered down.
#
# NOTE ON SYNTAX: amixer takes bare switch tokens. 'on'/'off' drive a PLAYBACK
# switch and 'cap'/'nocap' a CAPTURE switch. The bracketed forms copied out of
# "amixer contents" output ([on], [off]) are silently ignored with exit 0, and
# 'on' on a capture-only switch such as ADC fails outright. An earlier version
# of this block used both, so every switch here stayed at its reset value: the
# DAC->mixer->speaker path and the ADC were all muted and the radio had no
# audio in either direction. Keep these bare, and keep 'set -e' above so a
# future typo aborts first boot instead of shipping a silent radio.
#
# LEVELS, measured on hardware 2026-11-14. Net output = Speaker Analog + Driver.
#   'Speaker Analog' is a uniform 0.5 dB/step fader: dB = (value - 127) * 0.5
#   default listening level  -13.0 dB  = analog  89 (-19.0 dB) + driver 0 (+6 dB)
#   highest distortion-free  +14.5 dB  = analog 120 ( -3.5 dB) + driver 2 (+18 dB)
# Driver 2 with analog 127 (+18 dB) and driver 3 (+24 dB) are both audibly too
# loud/distorted. volume_ctrl.sh should fade 'Speaker Analog' and raise
# 'Speaker Driver' only for the top of the range, so the driver gain stays low
# (less amplified noise) at normal listening levels.
amix() { amixer -q -c AUDIO sset "$@"; }

# The AUDIO card is registered by simple-audio-card, which can finish after
# multi-user.target is reached, and this unit has no ordering against it. Wait
# for the card instead of letting 'set -e' abort provisioning half way: the
# services are disabled at the top of this script and ExecStartPost only runs
# on success, so an abort here leaves a board with no GUI and no audio.
n=0
until amixer -c AUDIO sget 'Speaker Analog' >/dev/null 2>&1; do
    n=$((n + 1))
    if [ "$n" -ge 60 ]; then
        echo "ERROR: sound card AUDIO did not appear within 30s" >&2
        exit 1
    fi
    sleep 0.5
done

# playback: DAC -> output mixer -> class-D speaker
amix 'DAC' 127                          # 0.0 dB. +3 dB clips the flowgraph digitally
amix 'DAC Left Input'  'Left Data'
amix 'DAC Right Input' 'Off'
amix 'Output Left From Left DAC'   on
amix 'Output Right From Right DAC' off
amix 'Output Left From MIC1LP'  off
amix 'Output Left From MIC1RP'  off
amix 'Output Right From MIC1RP' off
amix 'Speaker Analog' 89
amix 'Speaker Driver' 0 on
amix 'Speaker' on

# headphone output unused
amix 'HP Analog' 0
amix 'HP Driver' 0 off
amix 'HP Left'  off
amix 'HP Right' off
amix 'HP Output Driver Power-On time' '0us'
amix 'HP Output Driver Ramp-up step' '0ms'

# capture: electret on MIC1RP -> Mic PGA -> ADC
amix 'MIC1LM M-Terminal' 'Off'
amix 'MIC1LM P-Terminal' 'Off'
amix 'MIC1LP P-Terminal' 'Off'
amix 'MIC1RP P-Terminal' 'FFR 10 Ohm'
# Mic PGA is set for headroom, not loudness. Measured 2026-11-14 at +12 dB:
#   - The low-frequency content is NOT mains hum. There is no 50 Hz fundamental
#     or harmonic series; the energy sits below 20 Hz (DC offset ~0.006 plus
#     drift) and codec2 band-limits it away. What actually limits gain is
#     impulsive peaks that survive a high-pass (46 dB crest factor).
#   - Worst case measured, USB tethered with the speaker on: raw peak
#     -8.4 dBFS, so ~8 dB of headroom. On battery it is ~11 dB better.
#   - The old value of 99 (+49.5 dB) saturated the ADC outright.
#   - Leaving the speaker output path powered (set above) improves the mic
#     noise floor by ~10 dB in the speech band: with the output mixer powered
#     down DAPM drops the codec bias and the ADC floor rises from -56 to
#     -47 dBFS. Do not disable the output path to save power.
#   - A USB connection to a mains-powered host costs a further ~16 dB in the
#     speech band, so disregard any mic measurement taken while tethered.
# +20 dB was confirmed on air against an external HT. That is close to the
# ceiling: the impulsive peaks leave ~8 dB of headroom at +12 dB when tethered,
# so going much above +20 dB risks clipping them. If more modulation is ever
# needed, add makeup gain in the flowgraph behind a high-pass rather than here
# - the aic31xx driver exposes no HPF, AGC or processing-block control.
amix 'Mic PGA' 40                       # +20.0 dB
amix 'ADC Fine' 3
amix 'ADC' 40 cap                       # 0.0 dB. 'cap', not 'on'

amix 'Volume Soft Stepping' 'disabled'

alsactl store

# A U-Boot environment saved on eMMC survives reflashing and overrides the
# compiled-in defaults, so set the boot settings explicitly
echo "Setting U-Boot environment..."
set_uboot_env() {
    if [ "$(fw_printenv -n "$1" 2>/dev/null)" != "$2" ]; then
        fw_setenv "$1" "$2" || echo "WARN: could not set U-Boot $1"
    fi
}
set_uboot_env bootdelay 0
set_uboot_env bootlist "emmc_ul sd_ul"

echo "Enable services..."
systemctl enable linht-zmq-proxy.service
systemctl enable linht-volume-ctrl.service
systemctl enable linht-gui-test.service
systemctl disable linht-first-boot.service

echo "First-boot script completed, disabling service."

reboot