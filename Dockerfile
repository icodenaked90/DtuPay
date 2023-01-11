FROM ubuntu:latest
RUN apt-get update -y && apt install maven -y
COPY . .
RUN /build_and_run.sh