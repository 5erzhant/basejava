import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countResumes = 0;

    void clear() {
        for (int i = 0; i < countResumes; i++) {
            storage[i] = null;
        }
        countResumes = 0;

        System.out.println("Хранилище очищенно.");
    }

    void save(Resume r) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Введенный uuid уже существует.");
                return;
            }
        }
        if (countResumes < storage.length) {
            storage[countResumes] = r;
            countResumes++;
        } else {
            System.out.println("Хранилище переполнено!");
        }

    }

    void delete(String uuid) {
        boolean isExist = false;
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                isExist = true;
                System.out.println("uuid: " + storage[i].uuid + " удален.");
                for (int j = i; j < countResumes; j++) {
                    if (j < countResumes - 1) {
                        storage[j] = storage[j + 1];
                    }
                }
                storage[countResumes - 1] = null;
                countResumes--;
            }
        }
        if (!isExist) {
            System.out.println("Введенный uuid не существует.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Введенный uuid не найден.");
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, this.size());
    }

    int size() {
        return countResumes;
    }
}
