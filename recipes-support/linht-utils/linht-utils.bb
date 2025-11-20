SUMMARY = "Utilities for the LinHT handheld transceiver"
HOMEPAGE = "https://github.com/M17-Project/LinHT-utils"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d41d8cd98f00b204e9800998ecf8427e"

SRC_URI = "git://github.com/M17-Project/LinHT-utils.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit systemd

DEPENDS = "libgpiod libpredict libsx1255 zeromq liblinht-ctrl alsa-lib libgpiod raylib cyaml"
RDEPENDS:${PN} = "bash libgpiod libpredict libsx1255 zeromq liblinht-ctrl alsa-lib libgpiod raylib cyaml"

SYSTEMD_SERVICE:${PN} = "volume-ctrl.service zmq-proxy.service gui-test.service first-boot.service"  
SYSTEMD_AUTO_ENABLE = "enable"

EXTRA_OEMAKE = ""

do_compile() {
    # sx1255-spi
    cd ${S}/sx1255
    ${CC} ${CFLAGS} ${LDFLAGS} -Wall -Wextra -O2 \
         sx1255-spi.c -o sx1255-spi -lgpiod -lsx1255 -lm

    # fb_test
    cd ${S}/tests/fb_test
    ${CC} ${CFLAGS} ${LDFLAGS} -Wall -Wextra -O2 \
         *.c -o fb_test -lpredict -lm


    # zmq_proxy
    cd ${S}/tests/zmq_proxy
    ${CC} ${CFLAGS} ${LDFLAGS} -Wall -Wextra -O2 \
         *.c -o zmq_proxy -lzmq -lasound


    # gui_test
    cd ${S}/tests/gui_test 
    ${CC} ${CFLAGS} ${LDFLAGS} -Wall -Wextra -O2 \
         *.c -o gui_test -lraylib -lsx1255 -lzmq -llinht-ctrl -lcyaml                                           
}

do_install() {

    # install script
    install -Dm 0755 ${WORKDIR}/firstboot.sh ${D}${bindir}/firstboot.sh

    install -d ${D}${bindir}
    install -m 0755 ${S}/sx1255/sx1255-spi      ${D}${bindir}/
    install -m 0755 ${S}/tests/fb_test/fb_test  ${D}${bindir}/
    install -m 0755 ${S}/scripts/volume_ctrl.sh ${D}${bindir}/
    install -m 0755 ${S}/tests/zmq_proxy/zmq_proxy ${D}${bindir}/
    install -m 0755 ${S}/tests/gui_test/gui_test   ${D}${bindir}/              

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/first-boot.service  ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/volume-ctrl.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/zmq-proxy.service   ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/gui-test.service       ${D}${systemd_unitdir}/system/ 

    # Full source tree for on-device inspection
    install -d ${D}/root/LinHT-utils
    cp -R ${S}/* ${D}/root/LinHT-utils/
}

FILES:${PN} += "/root/LinHT-utils \
                ${systemd_unitdir}/system/first-boot.service \
                ${systemd_unitdir}/system/volume-ctrl.service \
                ${systemd_unitdir}/system/zmq-proxy.service \
                ${systemd_unitdir}/system/gui-test.service"               

INSANE_SKIP:${PN} = "already-stripped dev-so ldflags file-rdeps"

SRC_URI += "file://firstboot.sh \
            file://first-boot.service \
            file://volume-ctrl.service \
            file://zmq-proxy.service \
            file://gui-test.service"                                     
