#!/bin/sh
set -e
echo "Running first-boot script..."

touch /usr/share/linht/setup_done
cd /usr/share/linht/grc
grcc /usr/share/linht/grc/som_m17_ptt.grc

systemctl restart gui-test

echo "First-boot script completed."