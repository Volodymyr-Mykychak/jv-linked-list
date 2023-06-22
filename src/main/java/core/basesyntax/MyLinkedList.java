package core.basesyntax;

import java.util.List;

/**
 * Клас `MyLinkedList` реалізує інтерфейс `MyLinkedListInterface` і представляє реалізацію
 * зв'язаного списку. Він зберігає елементи вузлів, які мають посилання на попередній і
 * наступний вузол.
 *
 * @param <T> тип даних, які зберігаються в списку
 */
public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head; // Голова списку
    private Node<T> tail; // Хвіст списку
    private int size; // Кількість елементів у списку

    /**
     * Додає елемент в кінець списку.
     *
     * @param value значення, яке потрібно додати
     */
    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        // якщо голова Null додаємо новий вузол до голови
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode; // якщо ні даємо ссилку next хвоста на новий вузол;
        }
        tail = newNode; // і робмимо новий вузол хвостом
        size++; // інкрементуємо кількість елементів ще на 1
    }

    /**
     * Додає елемент за певним індексом у списку.
     *
     * @param value значення, яке потрібно додати
     * @param index індекс, за яким потрібно додати елемент
     */
    @Override
    public void add(T value, int index) {
        // якщо індекс рівний розміру масиву
        if (index == size) {
            add(value); // додаємо значення
            return; // виходимо з функції
        }
        Node<T> getNode = getNode(index); // шукаємо вузол по індексу
        Node<T> getNodePrev = getNode.prev; // отримаємо попередній вузол
        Node<T> newNode = new Node<>(getNodePrev, value, getNode); // створюємо новий вузол вводимо попередній вузол, значення та вузол
        if (getNodePrev == null) {
            head = newNode; // якщо попередній вузол null додаємо його до голови
        } else {
            getNodePrev.next = newNode; //якщо ні тоді робимо ссиклу на нього через next
        }
        getNode.prev = newNode; // робимо силку попереднього вузла на новий вузол
        size++; // інкерментуємо елементи
    }

    /**
     * Додає список елементів у кінець поточного списку.
     *
     * @param list список, який потрібно додати
     */
    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element); // Додаємо кожен елемент до поточного списку
        }
    }

    /**
     * Повертає елемент за певним індексом у списку.
     *
     * @param index індекс, за яким потрібно отримати елемент
     * @return елемент за певним індексом
     */
    @Override
    public T get(int index) {
        // Отримуємо вузол за заданим індексом і повертаємо його значення
        return getNode(index).value;
    }

    /**
     * Замінює елемент за певним індексом у списку новим значенням.
     *
     * @param value нове значення
     * @param index індекс, за яким потрібно замінити елемент
     * @return старе значення, яке було замінено
     */
    @Override
    public T set(T value, int index) {
        // Отримуємо вузол за заданим індексом
        Node<T> setNode = getNode(index);

        // Зберігаємо попереднє значення вузла
        T oldValue = setNode.value;

        // Встановлюємо нове значення вузла
        setNode.value = value;

        // Повертаємо попереднє значення
        return oldValue;
    }

    /**
     * Видаляє елемент за певним індексом у списку.
     *
     * @param index індекс, за яким потрібно видалити елемент
     * @return видалений елемент
     */
    @Override
    public T remove(int index) {
        // Отримуємо вузол за заданим індексом
        Node<T> removeNode = getNode(index);

        // Видаляємо вузол і повертаємо його значення
        return unlink(removeNode).value;
    }

    /**
     * Видаляє певний об'єкт зі списку.
     *
     * @param object об'єкт, який потрібно видалити
     * @return true, якщо об'єкт був успішно видалений; false, якщо об'єкт не знайдено у списку
     */
    @Override
    public boolean remove(T object) {
        // Починаємо пошук з голови списку
        Node<T> node = head;

        while (node != null) {
            // Перевіряємо, чи має поточний вузол вказане значення
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                // Якщо так, видаляємо вузол і повертаємо true
                unlink(node);
                return true;
            }
            node = node.next;
        }

        // Якщо елемент не знайдено, повертаємо false
        return false;
    }

    /**
     * Повертає кількість елементів у списку.
     *
     * @return кількість елементів у списку
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Перевіряє, чи є список пустим.
     *
     * @return true, якщо список пустий; false, якщо список містить елементи
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Перевіряє, чи індекс знаходиться в межах списку.
     * Викидає виняток `IndexOutOfBoundsException`, якщо індекс виходить за межі списку.
     *
     * @param index індекс для перевірки
     */
    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds, for index = " + index);
        }
    }

    /**
     * Повертає вузол за певним індексом у списку.
     *
     * @param index індекс вузла
     * @return вузол за певним індексом
     */
    private Node<T> getNode(int index) {
        // Перевіряємо допустимість індексу
        checkIndex(index);

        Node<T> node;

        if (index < size * 0.5) {
            // Якщо індекс знаходиться в першій половині списку, починаємо пошук з голови
            node = head;
            for (int i = 0; i < index; i++) {
                // Переходимо до наступного вузла
                node = node.next;
            }
        } else {
            // Якщо індекс знаходиться в другій половині списку, починаємо пошук з хвоста
            node = tail;
            for (int i = size - 1; i > index; i--) {
                // Переходимо до попереднього вузла
                node = node.prev;
            }
        }

        return node;
    }

    /**
     * Видаляє вузол зі списку.
     *
     * @param removeNode вузол, який потрібно видалити
     * @return видалений вузол
     */
    private Node<T> unlink(Node<T> removeNode) {
        if (removeNode == head) {
            // Якщо вузол є головою списку
            head = removeNode.next;
            removeNode.prev = null;
        } else if (removeNode == tail) {
            // Якщо вузол є хвостом списку
            removeNode.prev.next = null;
        } else {
            // Якщо вузол знаходиться середині списку
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
        }

        // Зменшуємо розмір списку
        size--;

        // Повертаємо видалений вузол
        return removeNode;
    }

    /**
     * Внутрішній клас, що представляє вузол зі значенням у списку.
     *
     * @param <T> тип даних вузла
     */
    private class Node<T> {
        private T value; // Значення вузла
        private Node<T> prev; // Посилання на попередній вузол
        private Node<T> next; // Посилання на наступний вузол

        /**
         * Конструктор класу Node.
         *
         * @param prev  попередній вузол
         * @param value значення вузла
         * @param next  наступний вузол
         */
        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
