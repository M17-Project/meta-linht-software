FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://20-usb0.network"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/20-usb0.network ${D}${sysconfdir}/systemd/network/
}

# Add this line to package the new file
FILES:${PN} += "${sysconfdir}/systemd/network/20-usb0.network"