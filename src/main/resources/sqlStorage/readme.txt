Если в запросе есть переменные типа месяц (mm) и год (yyyy)
необходимо заменить конструкцию
TO_DATE('" + month + "." + year + "', 'mm.yyyy')
на вот такую анотацию -
@InsertDateParam

пример:
Было:
select *
  from table t
 where t.date = TO_DATE('01.2018', 'mm.yyyy')
 group by t.something

Стало:
select *
  from table t
 where t.date = @InsertDateParam
 group by t.something