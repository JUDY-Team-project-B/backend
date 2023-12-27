# Hang Out

![image](https://github.com/Team-hangout/backend/assets/101315462/c5b1188e-91f6-4a63-94c5-56fc4262a700)

# 프로젝트 소개
가고 싶은 여행지를 정해서 같이 여행 갈 사람을 모집하거나, 직접 모임에 참여할 수 있는 여행 동행 커뮤니티 사이트입니다.

# 개발 기간
2023.4월 ~ 2023.11월

# 기술스택 - 각 포지션별로
## **:zap: Tech Stack**

![image](https://github.com/Team-hangout/backend/assets/101315462/ebf3a20f-b7ce-4dd6-8cd1-6929fa08a20c)


# start 방법

```
cd “YOUR_DOWNLOAD_LOCATION”

git clone --recursive https://github.com/PRP-for-your-portrait-right-protection/docker-repo.git

## insert configFile (m_config.py , module_config.py)

docker-compose -f docker-compose.yml up -d --build

```

### Setting File


- docker-repo/backend-repo/bucket/m_config.py
- docker-repo/celery-repo/bucket/m_config.py

```python
AWS_ACCESS_KEY = "YOUR_AWS_ACCESS_KEY"
AWS_SECRET_ACCESS_KEY = "YOUR_AWS_SECRET_ACCESS_KEY"
AWS_S3_BUCKET_REGION = "YOUR_AWS_S3_BUCKET_REGION"
AWS_S3_BUCKET_NAME = "YOUR_AWS_S3_BUCKET_NAME"
AWS_S3_BUCKET_URL = "YOUR_AWS_S3_BUCKET_URL"
```




- docker-repo/backend-repo/module/module_config.py 

```python
SECRET_KEY = "YOUR_TOKEN_SECRET_KEY"
TOKEN_EXPIRED = 3600 #3600 sec, If you want longer, you can change this time.
```

# 팀원 역할

| Name    | 박수현                                       |정태원                               | 박수연                                        | 조성현      | 이민지 | 박준혁                              |
| ------- | --------------------------------------------- | ------------------------------------ | --------------------------------------------- | --------------------------------------- | -----| ----- |
| Role    |   PM, Backend, DevOps   |    Frontend, DevOps     | Backend, DevOps | Frontend | Frontend | Backend, AI, DevOps |
| Github  | [@vivian0304](https://github.com/vivian0304) | [@teawon](https://github.com/teawon) | [@PARK-Su-yeon](https://github.com/PARK-Su-yeon) | [@vixloaze](https://github.com/vixloaze) | [@alswlfl29](https://github.com/alswlfl29)| [@JHPark02](https://github.com/JHPark02)|
