rate = 5.0
per = 10.0
allowance = rate
last_check = 0


def message(current):
    global last_check
    global allowance
    time_passed = current - last_check
    print "time_passed", time_passed
    last_check = current
    allowance += time_passed * (rate / per)
    print "allowance:", allowance
    if allowance > rate:
        global allowance
        allowance = rate  # throttle
    if allowance < 1.0:
        print "discard_message at ", current
    else:
        print "forward_message at ", current
        global allowance
        allowance -= 1.0
    print

message(1)
message(1)
message(1)
message(1)
message(1)
message(1)

message(3)
message(4)
message(4)
message(5)
message(6)
message(7)
message(7)
message(18)
message(19)
message(20)
message(21)
message(22)
message(23)
