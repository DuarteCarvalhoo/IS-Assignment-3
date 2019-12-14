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
    else:
        print("Insert a valid endpoint/parameters.")

if __name__ == "__main__":
    main()