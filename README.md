# 카테고리 서비스
> 카테고리 관리 API 서비스.

카테고리에 대한 저장/수정/삭제/조회 기능 Rest API 제공합니다.

## 설치 방법
필수 설치 패키지
```text
java >= 1.8
```

OS X & 리눅스: (저장소 clone 후 category-service.sh 스크립트 사용)

```sh
sh category-service.sh [build|start|stop|restart]
```

## Data Schema
```text
* category_id : 카테고리 시스템아이디
* name : 카테고리명
* is_delete : 삭제여부
* parent_id : 상위카테고리 시스템아이디
* created_at : 생성일자 
* updated_at : 수정일자 
```

## API 사용 예제
1. 카테고리 생성
```http request
POST http://{{host}}:{{port}}/category
Content-Type: application/json
```
request body : 신규 카테고리 생성시
```json
{
  "name" : "식품",
  "children" : [
    {
      "name" : "한식",
      "children" : [
        {"name" :  "국 및 찌개"},
        {"name" :  "사이드 반찬"},
        {"name" :  "메인 요리"}
      ]
    },
    {
      "name" : "일식",
      "children" : [
        {"name" :  "국"},
        {"name" :  "사이드 요리"},
        {"name" :  "메인 요리"}
      ]
    }
  ]
}
```
request body : 기존 카테고리의 하위 카테고리 생성시
```json
{
  "id" : 1,
  "children" : [
    {"name" : "곁들임 메뉴"}
  ]
}
```
response body : 응답형식으로 등록한 상위 카테고리의 시스템 아이디를 반환한다.
```json
{
	"success": true,
	"code": "0000",
	"response": {
		"categoryId": 1
	}
}
```
2. 카테고리 수정
```http request
PATCH http://{{host}}:{{port}}/category/{{categoryId}}
Content-Type: application/json; charset=utf-8
{
	"id" : 2,
    "name" : "중식",
	"children" : [
		{
            "id" : 2,
            "name" : "면요리"
		}
	]
}
```
response body
```json
{
	"success": true,
	"code": "0000"
}
```

3. 카테고리 삭제
```http request
DELETE http://{{host}}:{{port}}/category/{{categoryId}}
```
response body
```json
{
	"success": true,
	"code": "0000"
}
```
4. 카테고리 조회
* parentId가 존재하는 경우 해당 카테고리 하위의 카테고리가 조회하며
* 해당 파라미터가 없는 경우 저장된 모든 카테고리가 조회된다.
```http request
GET http://{{host}}:{{port}}/category?parentId={{parentId}}
```
response body
```json
{
  "success": true,
  "code": "0000",
  "response": {
    "categories": [
      {
        "categoryId": 1,
        "name": "의류",
        "children": [
          {
            "categoryId": 2,
            "name": "상의",
            "children": [
              {
                "categoryId": 6,
                "name": "아우터",
                "children": [
                  {
                    "categoryId": 7,
                    "name": "얇은"
                  },
                  {
                    "categoryId": 8,
                    "name": "두꺼운"
                  }
                ]
              }
            ]
          },
          {
            "categoryId": 3,
            "name": "하의",
            "children": [
              {
                "categoryId": 4,
                "name": "속옷"
              },
              {
                "categoryId": 5,
                "name": "양말"
              }
            ]
          }
        ]
      }
    ]
  }
}
```

## 정보

 weleek@gmail.com

 MIT 라이센스를 준수하며 [LICENSE](https://github.com/weleek/category-service/blob/master/LICENSE) 에서 자세한 정보를 확인할 수 있습니다.

[https://github.com/weleek/category-service](https://github.com/weleek/category-service)
