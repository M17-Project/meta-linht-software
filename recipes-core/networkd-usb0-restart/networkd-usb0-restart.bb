SUMMARY = "Systemd service to restart networkd after boot for usb0"
LICENSE = "MIT"

SRC_URI = "file://networkd-usb0-restart.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "networkd-usb0-restart.service"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/networkd-usb0-restart.service ${D}${systemd_system_unitdir}/
}
