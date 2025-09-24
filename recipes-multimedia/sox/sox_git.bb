SUMMARY = "SoX - Sound eXchange, Swiss Army knife of sound processing"
DESCRIPTION = "SoX is a cross-platform command line utility that can convert \
various formats of computer audio files in to other formats. It can also \
apply various effects to these sound files during the conversion process."
HOMEPAGE = "https://github.com/chirlu/sox"
LICENSE = "LGPL-2.1 & GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=fbc093901857fcd118f065f900982c24 \
                    file://LICENSE.GPL;md5=751419260aa954499f7abaabaa882bbe"
SECTION = "multimedia"
DEPENDS = "libao libmad libid3tag libvorbis libogg flac alsa-lib libsndfile1 autoconf-archive-native"

SRC_URI = "git://github.com/chirlu/sox.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

# Remove all PACKAGECONFIG until we verify what options are supported
PACKAGECONFIG ??= ""

# Start with minimal EXTRA_OECONF options
EXTRA_OECONF = "--disable-openmp"

# SoX uses some non-standard configure checks
do_configure:prepend() {
    # Generate configure script if it doesn't exist
    if [ ! -f ${S}/configure ]; then
        cd ${S} && autoreconf -fiv
    fi
}

PACKAGES =+ "libsox libsox-dev"
FILES:libsox = "${libdir}/libsox.so.*"
FILES:libsox-dev = "${libdir}/libsox.so ${libdir}/libsox.la \
                   ${libdir}/pkgconfig/sox.pc ${includedir}/sox.h"
RDEPENDS:${PN} = "libsox"
