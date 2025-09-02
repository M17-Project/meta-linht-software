SUMMARY = "Web based display for GNU Radio applications"
DESCRIPTION = "The module provides various sinks and widgets to allow interaction with the live GNU Radio applications remotely over the network. Uses Bokeh's client API and streaming features."
HOMEPAGE = "https://github.com/gnuradio/gr-bokehgui"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "gnuradio python3-pybind11 python3-numpy-native"
RDEPENDS:${PN} = "gnuradio python3-bokeh nodejs python3-pybind11"

PV = "1.0+git${SRCPV}"
SRCREV = "e956049486047f45c4799fb8a5fe17906e861eb4"

SRC_URI = "git://github.com/gnuradio/gr-bokehgui.git;branch=master;protocol=https \
           file://0001-cmake-make-bokeh-optional.patch \
"

S = "${WORKDIR}/git"

inherit cmake python3native

EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=Release \
    -DENABLE_DOXYGEN=OFF \
    -DGR_PYTHON_DIR=${PYTHON_SITEPACKAGES_DIR} \
"

# Bokeh and NodeJS are runtime dependencies
RDEPENDS:${PN} += " \
    python3-bokeh \
    python3-numpy \
    python3-tornado \
    nodejs \
    bash \  
"

FILES:${PN} += " \
    ${datadir}/gnuradio/grc/blocks/*.yml \
    ${PYTHON_SITEPACKAGES_DIR}/bokehgui \
"

# Optional: Add netstat for port checking
RRECOMMENDS:${PN} += "net-tools"

COMPATIBLE_MACHINE = "(.*)"

# Silence “already-stripped” QA check for the main package
INSANE_SKIP:${PN} += "already-stripped"
