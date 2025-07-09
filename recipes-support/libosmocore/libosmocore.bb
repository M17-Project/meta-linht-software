SUMMARY = "Osmocom core libraries"
DESCRIPTION = "Core infrastructure libraries for Osmocom projects."
HOMEPAGE = "https://osmocom.org/projects/libosmocore"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://github.com/osmocom/libosmocore.git;protocol=https;branch=master"

SRCREV = "c17053a56275b2c38fa63c9038edddabbe0f296b"

S = "${WORKDIR}/git"

DEPENDS = "autoconf-archive libtalloc liburing libusb gnutls libmnl lksctp-tools"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-simd --disable-pcsc"
