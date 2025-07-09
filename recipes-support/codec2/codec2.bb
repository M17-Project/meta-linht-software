DESCRIPTION = "Codec2 from Git"
HOMEPAGE = "https://github.com/drowe67/codec2.git"
LICENSE = "LGPL-2.1"

DEPENDS += "codec2-native"

SRCREV = "96e8a19c2487fd83bd981ce570f257aef42618f9"
SRC_URI = "git://github.com/drowe67/codec2.git;protocol=https;branch=main"

LIC_FILES_CHKSUM = "file://COPYING;md5=776e198b48f3a1004a437be10854038b"

S = "${WORKDIR}/git"

inherit cmake

SRC_URI += "file://codec2-cmake-native-tool.patch"
EXTRA_OECMAKE = "-DGENERATE_CODEBOOK_NATIVE_PATH=${STAGING_BINDIR_NATIVE}/generate_codebook"

do_configure:prepend() {
    # Adjust SRC for your recipe's source directory
    CODEBOOK_SRC="${STAGING_BINDIR_NATIVE}"
    CODEBOOK_DST="${S}/src"

    install -m 0644 ${CODEBOOK_SRC}/codebook*.c ${CODEBOOK_DST}/
}

do_install:append() {
    # Ensure the target include directory exists
    install -d ${D}${includedir}/codec2

    # Install all header files from the source directory
    install -m 0644 ${S}/src/*.h ${D}${includedir}/codec2/
}