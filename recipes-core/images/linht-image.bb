require recipes-core/images/core-image-base.bb

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
"

