package game.dqn;

import org.deeplearning4j.rl4j.environment.Environment;
import org.deeplearning4j.rl4j.network.dqn.DQN;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.util.DataManager;
import org.nd4j.linalg.learning.config.Nesterovs;

import game.Game;

public class DQNTrainer {
    public static void main(String[] args) {
        GameEnvironment gameEnv = new GameEnvironment(new Game(new GameObserverImpl()));

        Environment<GameEnvironment> environment = new Environment<GameEnvironment>(gameEnv);

        DQNFactoryStdDense.Configuration configuration = DQNFactoryStdDense.Configuration.builder()
                .l2(0.01)
                .learningRate(0.001)
                .numLayer(3)
                .numHiddenNodes(16)
                .build();

        DQN<GameEnvironment> dqn = new DQN<>(environment, configuration);

        DQNBuilder builder = new DQNBuilder(dqn, environment)
                .build();

        AgentLearnerBuilder<GameEnvironment> agentLearnerBuilder = new AgentLearnerBuilder<>(builder, environment)
                .learningRate(0.001)
                .gamma(0.99)
                .epsilon(1.0)
                .minEpsilon(0.1)
                .epsilonDecay(1000);

        Agent<GameEnvironment> agent = agentLearnerBuilder.build();

        // Train the agent
        agent.train();
    }
}
