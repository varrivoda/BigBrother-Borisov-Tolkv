# BigBrotherApp
это конспект доклада Е.Борисова и К.Толкачова https://www.youtube.com/watch?v=4QpnCbi5QyQ Доклад про реактивщину в сложных приложениях, и как ее настроить. ProjectReact и WebFlux.

По задумке у нас есть почтальон (Печкин),который отправляет письма снеограниченной скоростью. Эти письма проверяет Большой Брат, но скорость его проверки ограничена. Если письмо подозрительное, онопередается Агенту Смиту, онужеработает с отправителем, и работает еще более медленно. Задача в том, чтобы оптимизировать тредлпулы, буферы, сгладить нагрузку, чтобы буферы не переполнялись, почтальоны отсылали письма с нужной скоростью,и т.д.  


11:00
Начнём с ProjectReact, 
Просто сделаем пруф оф concept и посмотрим если это как бы нам поможет потом на продаже
(скорее всеготак не будет:)
Есть у нас есть три персонажа - большой брат печкин и смит, Давайте попробуем что-нибудь
с помощью них сэмулировать на projectreactor'e, как это будет вообще выглядеть. 
11:35
Cделаем как бы какой-то простой тест, котором у нас там magic(?) соответственно. 
Первым из первым делом мы создаем всех этих трех ребят. Теперь наш Печкин должен сгенерировать какой-то поток писем, то есть мы говорим pechkin.letters(). 
12:19
Cделаем этот метод. Мы уже упомянули про projectReactor, там есть такая штука как Flux, то есть некоторый поток писем, в которой и месяца(?) новые сообщения, в нашем случае это будут объекты писем вот у нас уже есть какой-то подозрительный e-mail, с которым мы будем работать. И мы должны сгенерировать поток писем. так как это все-таки демка, сгенерируем простым способом, ну и пока больше нам ничего не нужно в принципе.
```
return Flux.generate(synchronousSink -> {synchronousSink.next(new SuspiciousEmail());
```
(кто не знаком совсем с концепцией Flux, можете плюс-минус представлять, что это стримы, все оч похоже)

13:02
потом покажем, что это было в нашем старом императивным коде, когда мы как воде когда мы писали без project реактор и нас с помощью спринг MVC, это было,условно, while(true), который как-то куда-то что-то отправлял, но здесь стоит вопрос - а с какой скоростью вообще вся эта шняга работает? к этому мы сейчас вернемся, давайте к нашему пруф-оф-концептиу впрнемся, у нас есть этот Flux, дальше мы должны ну передать его дальше, то есть мы должны сказать: "а теперь отдай его вот соответственно большому брату а он там ну типа как ты там проанализирует".
```
 pechkin.letters()
        .transform(BigBrother::analyze());
```

13:39
Cоздадим метод analyze(), возвращает уже проанализированнып письма Flux<AnalyzedEmail>, а на входе все те же самые подозрительные e-mail. Вот у нас есть наш  мы должны условно как ты его проанализировать, не будем заморачиваться как именно, просто return new AnalyzedEmail()
```
public Flux<AnalyzedEmail> analyze(Flux<SuspiciousEmail> suspiciousEmailFlux) {
        return suspiciousEmailFlux
                .log("bigbrother-")
                .map(suspiciousEmail -> new AnalyzedEmail());
    }
```
13:51
В общем то эта вся полезная работа которую он должен сделать но мы же как-то эмулируем всю эту работу поэтому нам нужно логировать (как долго работает, или еще что-то).
И то же самое мы сделаем с нашим Печкиным - мы скажем, чтобы логировалось нормально 
14:35 
Дальше мы должны сэмулировать более длительную работу, сделаем это самым кондовым способом 
```
.doOnNext(analyzedEmail->{TimeUnit.MILLISECONDS.sleep(50)});
```

14:55
сделаем чтобы эмулировать какую-то работу. 
15:01
и для того чтобы это работало отдельно от других сервисов и они не шли одним потоком....Да хотя потом прикрутим, а пока что так запустим. 

15:12
остался последний чувак в нашей цепочке - агент Smith, который производит карательные действия над теми кто пишет письма ```reactFor(smith::reactFor)``` , вот он тоже у нас должен что-то вернуть - он должен вернуть Flux<reactionForEmail> reactionForEmail (наказать, запретить, еще что-нибудь.. с React'ом не связано:)
да он в принципе делает ровно то же самое да - то есть мы тоже ставим у него лог и говорим что это у нас "smith-", чтобы мы видели что происходит. Затем мы делаем какую-то полезную работу - в нашем случае создаем супер полезный объект, и вот он у нас уходит ну и конечно же мы должны сэмулировать, что мы делали это очень
долго, в этот раз давайте подольше да - все-таки,более ответственная работа, полсекунды поспим.

16:13
Вроде похоже на правду, давайте смотреть что из этого вышло. Просто вызовем после всех "transform-ов" .blockLast(), и увидим что происходит: сейчас у нас должны появиться какие-то логи, и вся система должна генерировать эти письма с какой-то скоростью. 

16:47
смотрим что происходит: вот у нас пошли какие то сообщения видно что идут они не очень быстро вы помните должна мы хотим проверить мы хотим проверить что у нас есть бэк прыжок потому что печкин
17:00
генерит как не в себя эти сообщения, следующий работают медленнее, потому что они спят. Но при этом мы хотим видеть, что как бы это все же как-то сбалансируется-затормозится и никакие письма не пропадут, что просто кто-то будет этого печкина подтормаживать,и он будет с нужной скоростью эти сообщения генерить. В данный момент мы видим, что письма генерируется достаточно медленно - со скоростью в полсекунды, как раз как мы выставляли в самом медленном нашем сервисе. 

17:25
Но выполняются они все на одном потоке. То есть они на самом деле сейчас зависят друг от друга, этот поток которому мы запускаем приложение все они последовательны, это совершенно не интересно, потому что мы пытаемся симулировать ситуацию, в которой они