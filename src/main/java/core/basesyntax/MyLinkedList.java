package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> getNode = getNode(index);
        Node<T> getNodePrev = getNode.prev;
        Node<T> newNode = new Node<>(getNodePrev, value, getNode);
        if (getNodePrev == null) {
            head = newNode;
        } else {
            getNodePrev.next = newNode;
        }
        getNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = getNode(index);
        T previousNodeValue = setNode.value;
        setNode.value = value;
        return previousNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = getNode(index);
        return unlink(removeNode).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds, for index = " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size * 0.5) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private Node<T> unlink(Node<T> removeNode) {
        if (removeNode == head) {
            head = removeNode.next;
            removeNode.prev = null;
        } else if (removeNode == tail) {
            removeNode.prev.next = null;
        } else {
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
        }
        size--;
        return removeNode;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
