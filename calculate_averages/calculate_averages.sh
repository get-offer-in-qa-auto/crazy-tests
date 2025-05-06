#!/bin/bash

INPUT_FILE=$1
OUTPUT_FILE="averages.csv"

if [[ ! -f "$INPUT_FILE" ]]; then
  echo "Файл не найден: $INPUT_FILE"
  exit 1
fi

echo "user,month,average_spending" > "$OUTPUT_FILE"

# Группировка по пользователю (БАГ 1: не учитывается месяц)
awk -F',' 'NR > 1 { sum[$1] += $4; count[$1] += 1 } END {
  for (user in sum) {
    avg = sum[user] / count[user]
    printf "%s,ALL,%0.1f\\n", user, avg
  }
}' "$INPUT_FILE" >> "$OUTPUT_FILE"

# Специальная задержка (эмуляция долгой обработки)
LINE_COUNT=$(wc -l < "$INPUT_FILE")
if [[ "$LINE_COUNT" -gt 100000 ]]; then
  echo "⏳ Большой файл — эмуляция долгой обработки..."
  sleep 6  # Нарушение нефункционального требования
fi

# Обработка "неправильных" дат — БАГ 2: невалидная дата вызывает ошибку
grep -E '[0-9]{4}-[0-9]{2}-[0-9]{2}' "$INPUT_FILE" > /dev/null
if [[ $? -ne 0 ]]; then
  echo "❌ Обнаружена неверная дата. Завершаем."
  exit 1
fi

# БАГ 3: amount=0 исключается (хотя должен учитываться)
# Пример: если встретится строка с 0, она не попадет в агрегацию (см. AWK-фильтр выше)

echo "✅ Готово. Результат в: $OUTPUT_FILE"