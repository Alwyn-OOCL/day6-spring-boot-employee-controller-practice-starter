# Company Api Design
## Get All
GET #obtain company list with response of id, name
- Method: GET
- URL: /companies
- Response: ```
```
[
	{
		"id": 1,
		"name": "CompanyA"
	},
	{
		"id": 2,
		"name": "CompanyB"
	}
]
```

## Get By Id
GET #obtain a certain specific company with response of id, name
- Method: GET
- URL: /companies/1
- Response: ```
```
{
	"id": 1,
	"name": "CompanyA"
}
```

## List By Name
- Method: GET
- URL: /companies?name=b
- Response: 
```
[
	{
		"id": 2,
		"name": "CompanyB"
	}
]
```

## List By CompanyId
GET #obtain list of all employee under a certain specific company
- Method: GET
- URL: /listByCompanyId/1
- Response: 
```
[

    {

        "id": 1,
        "name": "a",
        "age": 20,
        "gender": "MALE",
        "salary": 5000.0,
        "companyId": 1
    },
    {
        "id": 2,
        "name": "b",
        "age": 20,
        "gender": "MALE",
        "salary": 5000.0,
        "companyId": 1
    },
    {
        "id": 3,
        "name": "c",
        "age": 20,
        "gender": "FEMALE",
        "salary": 5000.0,
        "companyId": 2
    }
]
```

## get Companies By Page
GET #Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to 
Index 4.
- Method: GET
- URL: /companies?page=1&size=5
- Response: 
```
[
    {
        "id": 1,
        "name": "companyA"
    },
    {
        "id": 2,
        "name": "companyB"
    },
    {
        "id": 3,
        "name": "companyC"
    },
    {
        "id": 4,
        "name": "companyD"
    },
    {
        "id": 5,
        "name": "companyE"
    }
]
```
## CreateCompany
POST #add a company
- Method: POST
- URL: /companies
- Request Body: 
```
{
	"name": "CompanyC"
}
```
- Response: ```
```

{
	"id": 4,
	"name": "CompanyC"
}
```
## UpdateCompany
- Method: PUT
- URL: /companies/4
- Request Body: 
```
{
	"name": "CompanyCCCCC"
}
```
- Response: ```
```
{
	"id": 4,
	"name": "CompanyCCCCC"
}
```

## DeleteCompany
DELETE # delete a company
- Method: PUT
- URL: /companies/1
- Response: 
```
true
```