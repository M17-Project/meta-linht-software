DESCRIPTION = "This is the custom LinHT image"

inherit core-image

IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    tools-sdk \
    package-management \
    splash \
    nfs-client \
    tools-debug \
    ssh-server-openssh \
    hwcodecs \
"

IMAGE_INSTALL += "\
    gnuradio \
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

