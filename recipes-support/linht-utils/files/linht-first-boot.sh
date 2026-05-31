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
# TODO -> New codec
#amixer -q -c wm8960audio sset 'Headphone' 127,127
#amixer -q -c wm8960audio sset 'Speaker' 127,127
#amixer -q -c wm8960audio sset 'Speaker AC' 5
#amixer -q -c wm8960audio sset 'Speaker DC' 4

#amixer -q -c wm8960audio sset 'Right Boost Mixer RINPUT1' on
#amixer -q -c wm8960audio sset 'Right Input Boost Mixer RINPUT1' 0
#amixer -q -c wm8960audio sset 'Right Input Mixer Boost' on
#amixer -q -c wm8960audio sset 'ADC Data Output Select' 'Left Data = Right ADC; Right Data = Right ADC'

alsactl store

echo "Enable services..."
systemctl enable linht-zmq-proxy.service
systemctl enable linht-volume-ctrl.service
systemctl enable linht-gui-test.service
systemctl disable linht-first-boot.service

echo "First-boot script completed, disabling service."

reboot