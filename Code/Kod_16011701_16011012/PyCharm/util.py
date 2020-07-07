import pickle
import json
import numpy as np

__locations = None
__data_columns = None
__model = None

def get_estimated_price(location, room, distance, bathroom, car, total, type):
    try:
        loc_index = __data_columns.index(location.lower())
        tmp = __data_columns.index(type.lower())
    except:
        loc_index = -1
        tmp = -1

    x = np.zeros(len(__data_columns))
    x[0] = room
    x[1] = distance
    x[2] = bathroom
    x[3] = car
    x[4] = total
    if loc_index >= 0:
        x[loc_index] = 1
    if tmp >= 0:
        x[tmp] = 1

    return round(__model.predict([x])[0], 2)


def load_saved_artifacts():
    print("loading saved artifacts...start")
    global  __data_columns
    global __locations

    with open("./artifacts/columns.json", "r") as f:
        __data_columns = json.load(f)['data_columns']
        __locations = __data_columns[5:]  # first 3 columns are room distance bathroom car total

    global __model
    if __model is None:
        with open('./artifacts/melbourne_prices_model.pickle', 'rb') as f:
            __model = pickle.load(f)
    print("loading saved artifacts...done")

def get_location_names():
    return __locations

def get_data_columns():
    return __data_columns

if __name__ == '__main__':
    load_saved_artifacts()
    print(get_estimated_price('port phillip city council',4,1,2,1,328,'h'))
    print(get_estimated_price('Yarra City Council',2,1,1,0,235,'h'))
    print(get_estimated_price('Yarra City Council',2,0,1,1,202,'h'))