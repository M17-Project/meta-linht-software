#!/bin/sh
set -e
echo "Running first-boot script..."

systemctl stop gui-test

cd /usr/share/linht/grc
grcc /usr/share/linht/grc/som_m17_ptt.grc

systemctl start gui-test

amixer -q -c wm8960audio sset 'Speaker' 127,127
amixer -q -c wm8960audio sset 'Speaker AC' 5
amixer -q -c wm8960audio sset 'Speaker DC' 4

amixer -q -c wm8960audio sset 'Right Boost Mixer RINPUT1' on
amixer -q -c wm8960audio sset 'Right Input Boost Mixer RINPUT1' 0
amixer -q -c wm8960audio sset 'Right Input Mixer Boost' on
amixer -q -c wm8960audio sset 'ADC Data Output Select' 'Left Data = Right ADC; Right Data = Right ADC'

alsactl store

echo "First-boot script completed, disabling service."

# TODO -> remove if it works with ExecStartPost now
# systemctl disable first-boot.service

