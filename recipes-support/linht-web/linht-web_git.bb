SUMMARY = "LinHT Web Manager - Lightweight web-based management interface"
DESCRIPTION = "A web-based management interface for LinHT featuring integrated terminal, file manager, and container management"
HOMEPAGE = "https://github.com/OE3ANC/linht-web"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://github.com/OE3ANC/linht-web.git;protocol=https;branch=main \
           file://linht-web.service \
           file://config.yaml"

SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

DEPENDS = "go-native"
RDEPENDS:${PN} = "systemd docker"

inherit systemd

do_compile[network] = "1"


SYSTEMD_SERVICE:${PN} = "linht-web.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile() {
    export GOOS=linux
    export GOARCH=arm64
    export CGO_ENABLED=0
    export GO111MODULE=on
    export GOPROXY=https://proxy.golang.org,direct
    export GOSUMDB=sum.golang.org
    
    cd ${S}
    
    # Initialize go.mod if it doesn't exist
    if [ ! -f "go.mod" ]; then
        go mod init github.com/OE3ANC/linht-web
        go mod tidy
    fi
    
    mkdir -p ${WORKDIR}/build
    go build -o ${WORKDIR}/build/linht-web-arm64 .
}

do_install() {
    install -d ${D}/opt/linht-web
    install -d ${D}/opt/linht-web/web
    install -d ${D}${systemd_system_unitdir}

    # Install the ARM64 binary
    install -m 0755 ${WORKDIR}/build/linht-web-arm64 ${D}/opt/linht-web/linht-web
    
    # Install web files if they exist
    if [ -d "${S}/web" ]; then
        cp -r ${S}/web/* ${D}/opt/linht-web/web/
    fi
    
    # Install configuration file
    install -m 0644 ${WORKDIR}/config.yaml ${D}/opt/linht-web/config.yaml
    
    # Install systemd service file
    install -m 0644 ${WORKDIR}/linht-web.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} = "/opt/linht-web/* \
               ${systemd_system_unitdir}/linht-web.service"

INSANE_SKIP:${PN} = "already-stripped"
