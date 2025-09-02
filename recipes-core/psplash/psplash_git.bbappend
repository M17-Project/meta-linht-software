FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "gdk-pixbuf-native"

PRINC = "8"

SRC_URI += "file://splash.png"
SRC_URI += "file://psplash-colors.h"

SPLASH_IMAGES = "file://splash.png;outsuffix=default"
COLORS = "file://psplash-colors.h"

PACKAGECONFIG:remove = "progress-bar"
