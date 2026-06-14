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

# TODO -> Not nice, find a better way to do this
echo "Setting up audio..."
amixer -q -c AUDIO sset 'Speaker'   Mono: Playback [on]
amixer -q -c AUDIO sset 'Speaker Analog' 89
amixer -q -c AUDIO sset 'Speaker Driver' 3
amixer -q -c AUDIO sset 'Mic PGA'   Mono: Capture 99 [83%] [49.50dB]
amixer -q -c AUDIO sset 'ADC'   Mono: Capture 49 [77%] [4.50dB] [on]
amixer -q -c AUDIO sset 'ADC Fine'   Mono: Capture 3 [75%] [0.30dB]
amixer -q -c AUDIO sset 'DAC' 133,133
amixer -q -c AUDIO sset 'DAC Left Input' 'Left Data'
amixer -q -c AUDIO sset 'DAC Right Input' 'Off'
amixer -q -c AUDIO sset 'HP Analog' 0,0
amixer -q -c AUDIO sset 'HP Driver' 0,0
amixer -q -c AUDIO sset 'HP Left'   Mono: Playback [off]
amixer -q -c AUDIO sset 'HP Output Driver Power-On time' '0us'
amixer -q -c AUDIO sset 'HP Output Driver Ramp-up step' '0ms'
amixer -q -c AUDIO sset 'HP Right'   Mono: Playback [off]
amixer -q -c AUDIO sset 'MIC1LM M-Terminal' 'Off'
amixer -q -c AUDIO sset 'MIC1LM P-Terminal' 'Off'
amixer -q -c AUDIO sset 'MIC1LP P-Terminal' 'Off'
amixer -q -c AUDIO sset 'MIC1RP P-Terminal' 'FFR 10 Ohm'
amixer -q -c AUDIO sset 'Output Left From Left DAC'   Mono: Playback [on]
amixer -q -c AUDIO sset 'Output Left From MIC1LP'   Mono: Playback [off]
amixer -q -c AUDIO sset 'Output Left From MIC1RP'   Mono: Playback [off]
amixer -q -c AUDIO sset 'Output Right From MIC1RP'   Mono: Playback [off]
amixer -q -c AUDIO sset 'Output Right From Right DAC'   Mono: Playback [off]
amixer -q -c AUDIO sset 'Volume Soft Stepping' 'disabled'

alsactl store

echo "Enable services..."
systemctl enable linht-zmq-proxy.service
systemctl enable linht-volume-ctrl.service
systemctl enable linht-gui-test.service
systemctl disable linht-first-boot.service

echo "First-boot script completed, disabling service."

reboot