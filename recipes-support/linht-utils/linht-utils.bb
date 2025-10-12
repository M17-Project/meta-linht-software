SUMMARY = "Utilities for the LinHT handheld transceiver"
HOMEPAGE = "https://github.com/M17-Project/LinHT-utils"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""
SRC_URI = "git://github.com/M17-Project/LinHT-utils.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

inherit systemd

DEPENDS = "libgpiod libpredict libsx1255"
RDEPENDS:${PN} = "libgpiod libpredict bash libsx1255"

SYSTEMD_SERVICE:${PN} = "volume-ctrl.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile() {
    # Compile sx1255-spi
    cd ${S}/sx1255
    ${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS} \
        -Wall -Wextra -O2 \
        sx1255-spi.c -o sx1255-spi \
        -lgpiod -lsx1255 -lm

    # Compile fb_test
    cd ${S}/tests/fb_test
    ${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS} \
        -Wall -Wextra -O2 \
        *.c -o fb_test \
        -lpredict -lm
}

do_install() {
    # Install binaries
    install -d ${D}${bindir}
    if [ -f ${S}/sx1255/sx1255-spi ]; then
        install -m 0755 ${S}/sx1255/sx1255-spi ${D}${bindir}/
    fi
    if [ -f ${S}/scripts/volume_ctrl.sh ]; then
        install -m 0755 ${S}/scripts/volume_ctrl.sh ${D}${bindir}/volume_ctrl.sh
    fi
    if [ -f ${S}/tests/fb_test/fb_test ]; then
        install -m 0755 ${S}/tests/fb_test/fb_test ${D}${bindir}/
    fi

    # Install systemd service files
    install -d ${D}${systemd_unitdir}/system

    # volume-ctrl service
    cat > ${D}${systemd_unitdir}/system/volume-ctrl.service << EOF
[Unit]
Description=LinHT Volume Control Service
After=multi-user.target
Wants=multi-user.target

[Service]
Type=simple
ExecStart=${bindir}/volume_ctrl.sh
Restart=always
RestartSec=5
User=root

[Install]
WantedBy=multi-user.target
EOF
}

FILES:${PN} += "${systemd_unitdir}/system/sx1255-spi.service ${systemd_unitdir}/system/volume-ctrl.service"
INSANE_SKIP:${PN} = "dev-so already-stripped ldflags"
