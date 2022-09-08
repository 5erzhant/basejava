/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int numbersOfResumes = 0;

    void clear() {
        for (int i = 0; i < numbersOfResumes; i++) {
            storage[i] = null;
            numbersOfResumes = 0;
        }
        System.out.println("Хранилище очищенно.");
    }

    void save(Resume r) {
        boolean isExist = false;
        for (int i = 0; i < numbersOfResumes; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                isExist = true;
                System.out.println("Введенный uuid уже существует.");
                break;
            }
        }
        if (!isExist) {
            if (numbersOfResumes != storage.length) {
                numbersOfResumes++;
                storage[numbersOfResumes - 1] = r;
            } else {
                System.out.println("Хранилище переполнено!");
            }
        }
    }

    void delete(String uuid) {
        boolean isExist = false;
        for (int i = 0; i < numbersOfResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                isExist = true;
                System.out.println("uuid: " + storage[i].uuid + " удален.");
                for (int j = i; j < numbersOfResumes; j++) {
                    if ((j + 1) != storage.length) {
                        storage[j] = storage[j + 1];
                    }
                }
                storage[numbersOfResumes - 1] = null;
                numbersOfResumes--;
            }
        }
        if (!isExist) {
            System.out.println("Введенный uuid не существует.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < numbersOfResumes; i++) {
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
        Resume[] resumes = new Resume[numbersOfResumes];
        System.arraycopy(storage, 0, resumes, 0, numbersOfResumes);
        return resumes;
    }

    int size() {
        return numbersOfResumes;
    }
}
