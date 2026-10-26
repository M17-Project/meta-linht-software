FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://20-usb0.network"
SRC_URI += "file://50-linht-powerkey.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/20-usb0.network ${D}${sysconfdir}/systemd/network/

    install -d ${D}${sysconfdir}/systemd/logind.conf.d
    install -m 0644 ${WORKDIR}/50-linht-powerkey.conf ${D}${sysconfdir}/systemd/logind.conf.d/
}

# Add this line to package the new file
FILES:${PN} += "${sysconfdir}/systemd/network/20-usb0.network"
FILES:${PN} += "${sysconfdir}/systemd/logind.conf.d/50-linht-powerkey.conf"