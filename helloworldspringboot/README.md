## Commands for building and running the application in AWS EKS

### Build the image 
mvn clean install

docker build -t hello-world-springboot:latest .

### Push the image to ECR
aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin <ecr-repo-id>.dkr.ecr.ap-south-1.amazonaws.com

docker tag hello-world-springboot:latest  <ecr-repo-id>.dkr.ecr.ap-south-1.amazonaws.com/hello-world-springboot:latest

docker push <ecr-repo-id>.dkr.ecr.ap-south-1.amazonaws.com/hello-world-springboot:latest


### Create EKS Cluster 
aws ec2 create-key-pair --region <zone> --key-name <key-pair-name>

eksctl create cluster --name <cluster-name> --region <zone> --with-oidc --ssh-access --ssh-public-key <key-pair-name> --node-type=t2.small

### Deploy in cluster
kubectl get nodes -o wide

kubectl get pods --all-namespaces -o wide

kubectl apply -f k8config.yaml

kubectl get services


### Delete EKS Cluster
eksctl delete cluster --name <cluster-name> --region <zone>

## Commands for building and running the application in GCP GKE

### Push the image to GCR

### Create GKE Cluster
gcloud container clusters create hello-world-cluster --machine-type e2-micro --zone us-central1-c

gcloud container clusters get-credentials hello-world-cluster --zone us-central1-c --project crazy-project-1

### Deploy in GKE cluster

### Delete GKE Cluster

gcloud container clusters delete hello-world-cluster --zone us-central1-c

