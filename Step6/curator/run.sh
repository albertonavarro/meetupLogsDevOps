docker build . -t curator_step_6
docker run --network host curator_step_6:latest
docker rmi curator_step_6 --force
