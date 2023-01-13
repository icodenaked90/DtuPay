FROM ubuntu:latest
RUN apt-get update -y && apt install maven -y
COPY . .
RUN chmod +x build_and_run.sh
RUN /build_and_run.sh