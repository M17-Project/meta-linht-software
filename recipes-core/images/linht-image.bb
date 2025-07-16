DESCRIPTION = "This is the custom LinHT image"

inherit core-image

IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    tools-sdk \
    package-management \
    nfs-client \
    tools-debug \
    ssh-server-openssh \
    hwcodecs \
"

# Tools
IMAGE_INSTALL += "\
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
"

# Gnuradio stuff
IMAGE_INSTALL += "\
    gnuradio \
    zeromq-dev \
    gr-adsb \
    gr-air-modes \
    gr-ais \
    gr-foo \
    gr-m17 \
    gr-ieee80211 \
    gr-lora-sdr \
    gr-osmosdr \
    gr-pager \
    gr-satellites \
    rtl433 \
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
"