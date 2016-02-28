__author__ = 'sharma.varun'
class Publisher:
    def __init__(self, publisher_service):
        self.publisher_service = publisher_service

    def publish(self, data, topic):
        self.publisher_service.send(data, topic)


class FilterUnit:
    def __init__(self):
        self.subscriber_map = {}

    def get_subscriber_list(self, topic):
        if topic in self.subscriber_map:
            return self.subscriber_map[topic]
        else:
            return []

    def set_subscriber_map(self, subscriber_map):
        self.subscriber_map = subscriber_map

    def unregister_subscriber(self, subscriber, topic):
        subscriber_map = self.subscriber_map
        if topic in subscriber_map and subscriber in subscriber_map[topic]:
            subscriber_map[topic].remove(subscriber)
        self.subscriber_map = subscriber_map

    def register_subscriber(self, subscriber, topic):
        subscriber_map = self.subscriber_map
        if topic in subscriber_map:
            subscriber_map[topic].append(subscriber)
        else:
            subscriber_map[topic] = [subscriber]
        self.subscriber_map = subscriber_map

class PublisherService:
    def __init__(self, filter_unit):
        self.filter_unit = filter_unit

    def send(self, data, topic):
        subscriber_list = self.filter_unit.get_subscriber_list(topic)
        for subscriber in subscriber_list:
            subscriber.receive(data)


class SubscriberService:
    def __init__(self, filter_unit):
        self.filter_unit = filter_unit

    def unregister_subscriber(self, subscriber, topic):
        self.filter_unit.unregister_subscriber(subscriber,topic)

    def register_subscriber(self, subscriber, topic):
        self.filter_unit.register_subscriber(subscriber,topic)


class Subscriber:
    def __init__(self, subscriber_service):
        self.subscriber_service = subscriber_service

    def receive(self, data):
        print "received data", data

    def subscribe(self, topic):
        self.subscriber_service.register_subscriber(self, topic)

