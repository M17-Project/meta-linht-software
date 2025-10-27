DESCRIPTION = "This is the custom LinHT image"

inherit core-image
inherit extrausers

# Add user linht with the password linht
EXTRA_USERS_PARAMS = "useradd -u 1200 -d /home/linht -s /bin/sh -p '\$6\$oe3ancwashere\$6./TTGz3AxcuInBHXiDZFdeaRkG.5WjEQfd4lGWNSylIRLLjshUbLeSdBfyLTJ0rh88WW/vc1SJyNjE.aabDO1' linht; \
                      usermod -a -G sudo,audio,dialout,plugdev,shutdown,docker linht;"

# Fix python symlink
ROOTFS_POSTPROCESS_COMMAND += "create_python_symlink; "

create_python_symlink() {
    if [ ! -e ${IMAGE_ROOTFS}/usr/bin/python ]; then
        ln -sf python3 ${IMAGE_ROOTFS}/usr/bin/python
    fi
}

# Image features
IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    tools-sdk \
    package-management \
    nfs-client \
    tools-debug \
    ssh-server-openssh \
    hwcodecs \
    splash \
"

# Tools
IMAGE_INSTALL += "\
    sudo \
    zsh \
    nano \
    cmake \
    alsa-tools \
    alsa-state \
    alsa-utils \
    alsa-lib \
    libgpiod \
    libgpiod-dev \
    pv \
    git \
    htop \
    perf \
    dtc \
    thrift \
    kmscube \
    psplash \
    linht-web \
    linht-utils \
    linht-share \
    linht-environment \
    evtest \
    ffmpeg \
    ffmpeg-dev \
    flac \
    flac-dev \
    sox \
    packagegroup-container \
    m17-packet \
"

IMAGE_INSTALL:append = " \
    e2fsprogs-resize2fs \
    parted \
    util-linux \
"

# Gnuradio stuff
IMAGE_INSTALL += "\
    gnuradio \
    gnuradio-dev \
    gnuradio-gr-utils \
    zeromq-dev \
    gr-foo \
    gr-m17 \
    gr-bokehgui \
"

# Libs
IMAGE_INSTALL += "\
    codec2 \
    codec2-dev \
    libm17 \
    libm17-dev \
    libtalloc \
    libosmocore \
    libosmocore-dev \
    libthrift \
    freetype \
    freetype-dev \
    libsdl2 \
    libsdl2-dev \
    raylib \
    raylib-dev \
    libgpiod \
    libgpiod-dev \
    libpredict \
    libpredict-dev \
    cyaml \
    cyaml-dev \
    lvgl \
    lvgl-dev \
    libsx1255 \
    libsx1255-dev \
    libm17-t9 \
    libm17-t9-dev \
    libsndfile1 \
    libsndfile1-dev \
"

# Python Packages
IMAGE_INSTALL += "\
    python3-thrift \
    python3-encodec \
    python3-vocos \
    python3-numpy \
    python3-pip \
    python3-mako \
    python3-pyyaml \
    python3-scipy \
    python3-pybind11 \
    python3-pybind11-dev \
"
