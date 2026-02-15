SUMMARY = "Utilities for the LinHT handheld transceiver"
HOMEPAGE = "https://github.com/M17-Project/LinHT-utils"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d41d8cd98f00b204e9800998ecf8427e"

SRC_URI = "git://github.com/M17-Project/LinHT-utils.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit systemd

DEPENDS = "libgpiod libpredict libsx1255 zeromq liblinht-ctrl alsa-lib libgpiod raylib cyaml libm17"
RDEPENDS:${PN} = "bash libgpiod libpredict libsx1255 zeromq liblinht-ctrl alsa-lib libgpiod raylib cyaml libm17"

SYSTEMD_SERVICE:${PN} = "linht-volume-ctrl.service linht-zmq-proxy.service linht-gui-test.service linht-first-boot.service"  
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
         *.c -o gui_test -lm17 -lraylib -lsx1255 -lzmq -llinht-ctrl -lcyaml -lsqlite3 -lm -lpthread -ldl -lrt
}

do_install() {
     # install script
     install -Dm 0755 ${WORKDIR}/linht-first-boot.sh ${D}${bindir}/linht-first-boot.sh

     install -d ${D}${bindir}

     # Install programs with original names
     install -m 0755 ${S}/sx1255/sx1255-spi      ${D}${bindir}/
     install -m 0755 ${S}/tests/fb_test/fb_test  ${D}${bindir}/
     install -m 0755 ${S}/scripts/volume_ctrl.sh ${D}${bindir}/
     install -m 0755 ${S}/tests/zmq_proxy/zmq_proxy ${D}${bindir}/
     install -m 0755 ${S}/tests/gui_test/gui_test   ${D}${bindir}/

     # Create symlinks with linht- prefix
     ln -sf sx1255-spi  ${D}${bindir}/linht-sx1255-spi
     ln -sf fb_test     ${D}${bindir}/linht-fb_test
     ln -sf volume_ctrl.sh ${D}${bindir}/linht-volume_ctrl.sh
     ln -sf zmq_proxy   ${D}${bindir}/linht-zmq_proxy
     ln -sf gui_test    ${D}${bindir}/linht-gui_test

     # Install systemd service files
     install -d ${D}${systemd_unitdir}/system
     install -m 0644 ${WORKDIR}/linht-first-boot.service  ${D}${systemd_unitdir}/system/
     install -m 0644 ${WORKDIR}/linht-volume-ctrl.service ${D}${systemd_unitdir}/system/
     install -m 0644 ${WORKDIR}/linht-zmq-proxy.service   ${D}${systemd_unitdir}/system/
     install -m 0644 ${WORKDIR}/linht-gui-test.service    ${D}${systemd_unitdir}/system/ 

     # Full source tree for on-device inspection
     install -d ${D}/root/LinHT-utils
     cp -R ${S}/* ${D}/root/LinHT-utils/
}

FILES:${PN} += "/root/LinHT-utils \
                ${systemd_unitdir}/system/linht-first-boot.service \
                ${systemd_unitdir}/system/linht-volume-ctrl.service \
                ${systemd_unitdir}/system/linht-zmq-proxy.service \
                ${systemd_unitdir}/system/linht-gui-test.service"               

INSANE_SKIP:${PN} = "already-stripped dev-so ldflags file-rdeps"

SRC_URI += "file://linht-first-boot.sh \
            file://linht-first-boot.service \
            file://linht-volume-ctrl.service \
            file://linht-zmq-proxy.service \
            file://linht-gui-test.service"                                     
