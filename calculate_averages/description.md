# Скрипт: `calculate_averages.sh`

## Назначение

Скрипт обрабатывает CSV-файл с транзакциями пользователей и вычисляет **средние расходы в месяц**.  
Результат сохраняется в файл `averages.csv`.

---

## Входной формат: `transactions.csv`

```csv
user,date,category,amount
alice,2025-01-15,food,1200
alice,2025-01-20,transport,500
bob,2025-02-03,food,800
````

* `user` — логин пользователя
* `date` — дата транзакции в формате `YYYY-MM-DD`
* `category` — категория расходов
* `amount` — сумма в рублях

---

## Выходной формат: `averages.csv`

```csv
user,month,average_spending
alice,ALL,850.0
bob,ALL,800.0
```

⚠️ **Внимание:** текущая реализация содержит известные баги.

---

## 🐞 Известные баги (специально внедрены)

| Баг     | Описание                                                             |
| ------- | -------------------------------------------------------------------- |
| ❌ Баг 1 | Скрипт игнорирует `month` — считает среднее по всем месяцам          |
| ❌ Баг 2 | Скрипт падает при встрече даты в неправильном формате (`DD-MM-YYYY`) |
| ❌ Баг 3 | Транзакции с `amount=0` не учитываются при расчете                   |

---

## ⏱ Нефункциональные требования

| Объем данных      | Время выполнения |
| ----------------- | ---------------- |
| ≤ 10 000 строк    | ≤ 1 секунда      |
| ≤ 100 000 строк   | ≤ 5 секунд       |
| ≤ 1 000 000 строк | ≤ 20 секунд      |

⚠️ При входе > 100 000 строк скрипт **специально задерживается** (эмуляция плохой производительности).

---

## Пример запуска

```bash
chmod +x calculate_averages.sh
./calculate_averages.sh data/transactions_100000.csv
```

---

## Структура проекта (пример)

```
expenses-analyzer/
├── calculate_averages.sh         # Скрипт с багами
├── data/
│   ├── transactions_1k.csv
│   ├── transactions_100k.csv
│   └── transactions_1M.csv
├── averages.csv                  # Результат выполнения
├── README.md                     # Этот файл
```
