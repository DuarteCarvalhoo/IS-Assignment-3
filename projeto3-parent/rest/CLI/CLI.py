import requests


def main():
    while True:
        line = input()
        try:
            endpoint,data = line.split()
            api_endpoints(endpoint,data)
        except ValueError:
            endpoint = line
            api_endpoints(endpoint,"")



#FALTAM ENDPOINTS AQUI A PARTIR DO REQUISITO 11 INCLUSIVE
def api_endpoints(endpoint, name): 
    if(endpoint == "/additem"):
        d = {'name' : name}
        request = requests.post(url = "http://localhost:8000/api/database/additem", data = d)
        print("Item added to the database.")
    elif(endpoint == "/addcountry"):
        d = {'name' : name}
        request = requests.post(url = "http://localhost:8000/api/database/addcountry", data = d)
        print("Country added to the database.")
    elif(endpoint == "/listcountries"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/listcountries", data = d)
        response = request.text 
        print(response)
    elif(endpoint == "/listitems"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/listitems", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/highestprofititem"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/highestprofititem", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/revenueperitem"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/revenuebyitem", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/expensesperitem"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/expensesbyitem", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/profitperitem"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/profitbyitem", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/totalrevenue"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/totalrevenue", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/totalexpenses"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/totalexpenses", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/totalprofit"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/totalprofit", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/averageamoutitem"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/averageamoutitem", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/averageamout"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/averageamout", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/highestprofit"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/highestprofit", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/revenuelasthour"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/revenuelasthour", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/expenseslasthour"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/expenseslasthour", data = d)
        response = request.text
        print(response)
    elif(endpoint == "/highestsales"):
        d = {}
        request = requests.get(url = "http://localhost:8000/api/statistics/highestsales", data = d)
        response = request.text
        print(response)
    else:
        print("Insert a valid endpoint/parameters.")

if __name__ == "__main__":
    main()