package be.wouterversyck.slackintegration.repositories;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import reactor.core.publisher.Mono;

public class FunFactRepositoryImpl implements FunFactRepositoryCustom {

    private ReactiveMongoTemplate reactiveMongoTemplate;

    public FunFactRepositoryImpl(final ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<FunFact> getRandom() {
        SampleOperation sampleOperation = Aggregation.sample(1);
        Aggregation aggregation = Aggregation.newAggregation(sampleOperation);
        return reactiveMongoTemplate.aggregate(aggregation, "fun_facts", FunFact.class)
                .next();
    }
}
