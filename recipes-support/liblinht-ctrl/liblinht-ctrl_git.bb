SUMMARY = "Modular C library for controlling GPIO pins and PWM channels on LinHT"
DESCRIPTION = "A control library for the LinHT open-source software defined transceiver hardware, \
providing GPIO and PWM control functions for flashlight, LEDs, RF switches, and backlight controls"
HOMEPAGE = "https://github.com/M17-Project/liblinht-ctrl"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"

SRC_URI = "git://github.com/M17-Project/liblinht-ctrl.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

DEPENDS = "libgpiod"
RDEPENDS:${PN} = "libgpiod"

inherit pkgconfig

# Add include directory to CFLAGS and pass to make
EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS} -I${S}/include' LDFLAGS='${LDFLAGS}'"

do_compile() {
    oe_runmake all
}

do_install() {
    oe_runmake install DESTDIR=${D} PREFIX=${prefix}
}

# Package the outputs properly
PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev"

FILES:${PN} = "${libdir}/liblinht-ctrl.so.*"
FILES:${PN}-dev = "${includedir}/liblinht-ctrl.h ${libdir}/liblinht-ctrl.so ${libdir}/pkgconfig/*"
FILES:${PN}-staticdev = "${libdir}/liblinht-ctrl.a"

# Allow native and SDK builds
BBCLASSEXTEND = "native nativesdk"
