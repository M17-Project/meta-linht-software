# recipes-core/linht-environment/linht-environment_1.0.bb
SUMMARY = "Custom environment variables and aliases for linht"
LICENSE = "CLOSED"

SRC_URI = "file://environment.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/environment.sh ${D}${sysconfdir}/profile.d/
}

FILES:${PN} = "${sysconfdir}/profile.d/environment.sh"