SUMMARY = "LinHT-GUI Linux radio application with framebuffer display support"
DESCRIPTION = "Linux radio application with framebuffer display support for embedded radio hardware."
HOMEPAGE = "https://github.com/OE3ANC/linht-gui"
LICENSE = "GPL-3.0"

SRCREV = "9c2fa5661ed74761b9ba9172a9bffdced40ade31" 
SRC_URI = "git://github.com/OE3ANC/linht-gui.git;protocol=https;branch=main"

LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"


S = "${WORKDIR}/git"

inherit cargo

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/target/release/linht-gui ${D}${bindir}/linht-gui

    install -d ${D}${systemd_system_unitdir}
    cat << EOF > ${D}${systemd_system_unitdir}/linht-gui.service
[Unit]
Description=LinHT-GUI Radio Application

[Service]
Type=simple
WorkingDirectory=/root
ExecStart=${bindir}/linht-gui
Restart=on-failure

[Install]
WantedBy=multi-user.target
EOF
}

SYSTEMD_SERVICE_${PN} = "linht-gui.service"
