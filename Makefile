IMAGE_PREFIX = irianlp
IMAGE_NAME = ingestion-pipeline-standalone
IMG := $(IMAGE_PREFIX)/$(IMAGE_NAME)

APP_VERSION := latest

docker-build:
	sbt clean assembly
	./build-tools/remove-signatures.sh
#	docker build -t $(IMG):$(APP_VERSION) .

docker-push: docker-build
	docker push $(IMG):$(APP_VERSION)
