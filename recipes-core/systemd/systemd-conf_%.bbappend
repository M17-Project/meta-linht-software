FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://20-usb0.network"
SRC_URI += "file://50-linht-powerkey.conf"
SRC_URI += "file://10-linht-no-timesyncd.preset"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/20-usb0.network ${D}${sysconfdir}/systemd/network/

    install -d ${D}${sysconfdir}/systemd/logind.conf.d
    install -m 0644 ${WORKDIR}/50-linht-powerkey.conf ${D}${sysconfdir}/systemd/logind.conf.d/

    # No NTP on LinHT. The preset keeps systemd-timesyncd out of
    # sysinit.target.wants when the image runs "systemctl preset-all" ...
    install -d ${D}${systemd_unitdir}/system-preset
    install -m 0644 ${WORKDIR}/10-linht-no-timesyncd.preset ${D}${systemd_unitdir}/system-preset/

    # ... and masking the unit stops D-Bus activation of
    # org.freedesktop.timesync1 and "timedatectl set-ntp true" from starting it
    # behind our back. Undo on a running target with:
    #     systemctl unmask systemd-timesyncd.service
    install -d ${D}${sysconfdir}/systemd/system
    ln -sf /dev/null ${D}${sysconfdir}/systemd/system/systemd-timesyncd.service
}

# Add this line to package the new file
FILES:${PN} += "${sysconfdir}/systemd/network/20-usb0.network"
FILES:${PN} += "${sysconfdir}/systemd/logind.conf.d/50-linht-powerkey.conf"
FILES:${PN} += "${systemd_unitdir}/system-preset/10-linht-no-timesyncd.preset"
FILES:${PN} += "${sysconfdir}/systemd/system/systemd-timesyncd.service"
