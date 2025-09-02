SUMMARY = "LinHT-GUI radio frontend"
HOMEPAGE = "https://github.com/OE3ANC/linht-gui"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "https://github.com/OE3ANC/linht-gui/releases/latest/download/linht-gui-package.tar.gz \
           file://linht-gui.service"
SRC_URI[sha256sum] = "b50d878ea9093ce26533b998535936b4165cc38cb219cd8277b4f3f9cdf89ada"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "linht-gui.service"
SYSTEMD_AUTO_ENABLE = "enable"

# Skip QA checks for precompiled binary
INSANE_SKIP:${PN} = "ptest already-stripped"

do_install() {
    install -d ${D}/opt/linht-gui
    
    install -m 0755 ${S}/linht-gui ${D}/opt/linht-gui/
    cp -r ${S}/flowgraphs ${D}/opt/linht-gui/
    cp -r ${S}/fonts ${D}/opt/linht-gui/
}

do_install:append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/linht-gui.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "/opt/linht-gui ${systemd_unitdir}/system/linht-gui.service"
