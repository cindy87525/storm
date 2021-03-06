/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.apache.storm.kafka.trident;

import java.util.List;
import java.util.Map;
import org.apache.storm.kafka.Partition;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.IOpaquePartitionedTridentSpout;
import org.apache.storm.tuple.Fields;


public class OpaqueTridentKafkaSpout
    implements IOpaquePartitionedTridentSpout<
    List<GlobalPartitionInformation>,
    Partition,
    Map<String, Object>> {


    TridentKafkaConfig _config;

    public OpaqueTridentKafkaSpout(TridentKafkaConfig config) {
        _config = config;
    }

    @Override
    public Emitter<List<GlobalPartitionInformation>,
        Partition,
        Map<String, Object>> getEmitter(Map<String, Object> conf,
                                        TopologyContext context) {
        return new TridentKafkaEmitter(conf, context, _config, context
            .getStormId()).asOpaqueEmitter();
    }

    @Override
    public IOpaquePartitionedTridentSpout.Coordinator getCoordinator(
        Map<String, Object> conf,
        TopologyContext tc) {
        return new org.apache.storm.kafka.trident.Coordinator(conf, _config);
    }

    @Override
    public Fields getOutputFields() {
        return _config.scheme.getOutputFields();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}
