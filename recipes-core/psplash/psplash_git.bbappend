FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "gdk-pixbuf-native"

PRINC = "8"

SRC_URI += "file://splash.png"

SPLASH_IMAGES = "file://splash.png;outsuffix=default"
