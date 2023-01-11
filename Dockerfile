FROM ubuntu:latest
RUN apt-get update -y && apt install maven -y
COPY . .
CMD ["chmod +x", "/build_and_run.sh"]
CMD ["/build_and_run.sh"]
