__author__ = 'sharma.varun'
## This is the text editor interface.
## Anything you type or change here will be seen by the other person in real time.

class Location:
    def __init__(self,lat,lng,timestamp):
        self.lat = lat
        self.lng = lng
        self.timestamp = timestamp

    def __repr__(self):
        return "lat:"+ str(self.lat)+ " lng:"+ str(self.lng)+ " timestamp:"+ str(self.timestamp)

driver_location_dict = {}

def receive(driver_id, location):
    if driver_id in driver_location_dict:
        driver_location_dict[driver_id].append(location)
    else:
        driver_location_dict[driver_id] = [location]

def search(location_history, timestamp):
    latest_location = None
    for item in location_history:
        if item.timestamp <= timestamp:
            latest_location = item
        else:
            break
    return latest_location


def get_location_driver(driver_id, timestamp):
    if driver_id in driver_location_dict:
        location_history = sorted(driver_location_dict[driver_id],key = lambda x:x.timestamp)
        latest_location = search(location_history, timestamp)
        if latest_location:
            print latest_location
        else:
            print "no event before this timestamp:", timestamp
    else:
        print "latest location not available"


receive("1",Location(12.21,72.21,12))
receive("1",Location(12.21,72.22,10))
receive("1",Location(12.21,72.22,1))

print driver_location_dict
receive("2",Location(12.21,72.22,11))

get_location_driver("1","9")


#Given a list of left, right coordinates alongwith the height, determine the skyline of a city. Output can be in the form of x and y coordinates. eg. input:
# [l1, r1, h1], [l2, r2, h2]....
# output: {(x1, y1), (x2, y2)....}


# input = [(0,3,2),(1,6,1),(2,4,3),(5,7,4)]
# import heapq
# def find_skyline(input):
#     max_heap_by_height = heapq.maxheap(lambda x:x[2])
#     max_heap_by_time - heapq.maxheap(lambda x:x[0])
#     output = []
#     for item in input:
#         if max_heap_by_height:
#             top_height = max_heap_by_height.top()
#             top_time = max_heap_by_time.top()
#             if item[0] > time_heap[0]:
#             #remove elements from time heap
#             else:
#                 max_heap_by_height.insert((item[1],item[2]))
#             # start_time> end time of top request
#             if item[2] > a[1]:
#             #append to solution and insert its end time,height to heap
#             else:
#         #insert into heap
#
#         else
#             #append coordinates to solution
#             output.append()



