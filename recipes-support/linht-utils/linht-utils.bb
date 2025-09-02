SUMMARY = "Utilities for the LinHT handheld transceiver"
HOMEPAGE = "https://github.com/M17-Project/LinHT-utils"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://github.com/M17-Project/LinHT-utils.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

# Inherit systemd class
inherit systemd

# Build-time dependencies
DEPENDS = "libgpiod"

# Runtime dependencies - Added bash for the volume_ctrl.sh script
RDEPENDS:${PN} = "libgpiod bash"

# Systemd service configuration
SYSTEMD_SERVICE:${PN} = "volume-ctrl.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile() {
    # Build sx1255 component manually instead of using the problematic Makefile
    cd ${S}/sx1255
    
    # Compile directly with cross-compiler and proper include paths
    ${CC} ${CPPFLAGS} ${CFLAGS} ${LDFLAGS} \
        -Wall -Wextra -O2 \
        sx1255-spi.c -o sx1255-spi \
        -lgpiod -lm
}

do_install() {
    # Install sx1255 binaries
    install -d ${D}${bindir}
    
    # Install the built executable
    if [ -f ${S}/sx1255/sx1255-spi ]; then
        install -m 0755 ${S}/sx1255/sx1255-spi ${D}${bindir}/
    fi
    
    # Install volume control script
    if [ -f ${S}/scripts/volume_ctrl.sh ]; then
        install -m 0755 ${S}/scripts/volume_ctrl.sh ${D}${bindir}/volume_ctrl.sh
    fi
    
    # Install systemd service file
    install -d ${D}${systemd_unitdir}/system
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
    
    # Copy grc directory to /root/grc
    install -d ${D}/root/grc
    if [ -d ${S}/grc ]; then
        cp -r ${S}/grc/* ${D}/root/grc/
        # Set appropriate permissions
        find ${D}/root/grc -type f -exec chmod 644 {} \;
        find ${D}/root/grc -type d -exec chmod 755 {} \;
    fi
}

FILES:${PN} += "/root/grc ${systemd_unitdir}/system/volume-ctrl.service"

# Skip QA checks
INSANE_SKIP:${PN} = "dev-so already-stripped ldflags"
