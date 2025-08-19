package com.example.util;

import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (questionRepository.count() == 0) {
            System.out.println("No questions found in DB, seeding data...");

            List<Question> questions = Arrays.asList(
                // Science Questions
                createQuestion("Science", "What is the chemical symbol for Gold?", "[\"Au\", \"Ag\", \"G\", \"Go\"]", 0),
                createQuestion("Science", "Which planet is known as the Red Planet?", "[\"Earth\", \"Mars\", \"Jupiter\", \"Venus\"]", 1),
                createQuestion("Science", "What is the speed of light?", "[\"300,000 km/s\", \"150,000 km/s\", \"500,000 km/s\", \"1,000,000 km/s\"]", 0),
                createQuestion("Science", "What is the powerhouse of the cell?", "[\"Nucleus\", \"Ribosome\", \"Mitochondrion\", \"Chloroplast\"]", 2),
                createQuestion("Science", "How many bones are in the human body?", "[\"206\", \"201\", \"212\", \"209\"]", 0),
                createQuestion("Science", "What is the hardest natural substance on Earth?", "[\"Gold\", \"Iron\", \"Diamond\", \"Platinum\"]", 2),
                createQuestion("Science", "What gas do plants absorb from the atmosphere?", "[\"Oxygen\", \"Carbon Dioxide\", \"Nitrogen\", \"Hydrogen\"]", 1),
                createQuestion("Science", "What is the main component of the sun?", "[\"Hydrogen\", \"Helium\", \"Oxygen\", \"Carbon\"]", 0),
                createQuestion("Science", "Which of these is a mammal?", "[\"Shark\", \"Dolphin\", \"Tuna\", \"Octopus\"]", 1),
                createQuestion("Science", "What is the study of earthquakes called?", "[\"Seismology\", \"Geology\", \"Volcanology\", \"Meteorology\"]", 0),
                createQuestion("Science", "What is the chemical formula for table salt?", "[\"NaCl\", \"H2O\", \"CO2\", \"C6H12O6\"]", 0),
                createQuestion("Science", "Which element has the atomic number 1?", "[\"Helium\", \"Oxygen\", \"Hydrogen\", \"Carbon\"]", 2),
                createQuestion("Science", "What force keeps us on the ground?", "[\"Magnetism\", \"Gravity\", \"Friction\", \"Tension\"]", 1),
                createQuestion("Science", "What is the largest organ in the human body?", "[\"Heart\", \"Liver\", \"Brain\", \"Skin\"]", 3),
                createQuestion("Science", "Which disease is caused by a vitamin C deficiency?", "[\"Rickets\", \"Scurvy\", \"Beriberi\", \"Pellagra\"]", 1),
                createQuestion("Science", "What is the boiling point of water at sea level?", "[\"90°C\", \"100°C\", \"110°C\", \"120°C\"]", 1),
                createQuestion("Science", "What type of energy does a plant use for photosynthesis?", "[\"Kinetic\", \"Chemical\", \"Solar\", \"Thermal\"]", 2),
                createQuestion("Science", "Who developed the theory of relativity?", "[\"Isaac Newton\", \"Galileo Galilei\", \"Albert Einstein\", \"Nikola Tesla\"]", 2),
                createQuestion("Science", "How many hearts does an octopus have?", "[\"1\", \"2\", \"3\", \"4\"]", 2),
                createQuestion("Science", "What is the most abundant gas in Earth's atmosphere?", "[\"Oxygen\", \"Carbon Dioxide\", \"Nitrogen\", \"Argon\"]", 2),

                // History Questions
                createQuestion("History", "Who was the first President of the United States?", "[\"Abraham Lincoln\", \"Thomas Jefferson\", \"George Washington\", \"John Adams\"]", 2),
                createQuestion("History", "In which year did the Titanic sink?", "[\"1905\", \"1912\", \"1918\", \"1923\"]", 1),
                createQuestion("History", "What ancient civilization built the pyramids?", "[\"Greeks\", \"Romans\", \"Egyptians\", \"Persians\"]", 2),
                createQuestion("History", "Who wrote the 'Declaration of Independence'?", "[\"Benjamin Franklin\", \"John Hancock\", \"Thomas Jefferson\", \"George Washington\"]", 2),
                createQuestion("History", "The Magna Carta was signed in which country?", "[\"France\", \"Spain\", \"Italy\", \"England\"]", 3),
                createQuestion("History", "Which war was fought between the North and South regions of the United States?", "[\"Revolutionary War\", \"War of 1812\", \"Civil War\", \"World War I\"]", 2),
                createQuestion("History", "Who was the first man to walk on the moon?", "[\"Buzz Aldrin\", \"Yuri Gagarin\", \"Michael Collins\", \"Neil Armstrong\"]", 3),
                createQuestion("History", "What was the Renaissance a rebirth of?", "[\"Art and learning\", \"Religion\", \"Warfare\", \"Politics\"]", 0),
                createQuestion("History", "Who discovered America in 1492?", "[\"Ferdinand Magellan\", \"Christopher Columbus\", \"Vasco da Gama\", \"Marco Polo\"]", 1),
                createQuestion("History", "In which year did World War II end?", "[\"1942\", \"1945\", \"1950\", \"1939\"]", 1),
                createQuestion("History", "The ancient city of Rome was built on how many hills?", "[\"5\", \"7\", \"9\", \"10\"]", 1),
                createQuestion("History", "Who was the famous queen of ancient Egypt?", "[\"Nefertiti\", \"Cleopatra\", \"Hatshepsut\", \"Sobekneferu\"]", 1),
                createQuestion("History", "What empire was ruled by Genghis Khan?", "[\"Roman Empire\", \"Ottoman Empire\", \"Mongol Empire\", \"Persian Empire\"]", 2),
                createQuestion("History", "The Hundred Years' War was fought between which two countries?", "[\"England and France\", \"Spain and Portugal\", \"Germany and Russia\", \"Italy and Austria\"]", 0),
                createQuestion("History", "Who invented the printing press?", "[\"Johannes Gutenberg\", \"Leonardo da Vinci\", \"Galileo Galilei\", \"Isaac Newton\"]", 0),
                createQuestion("History", "What was the name of the ship that brought the Pilgrims to America in 1620?", "[\"Santa Maria\", \"Mayflower\", \"Discovery\", \"Susan Constant\"]", 1),
                createQuestion("History", "In which city was Archduke Franz Ferdinand assassinated, sparking WWI?", "[\"Vienna\", \"Budapest\", \"Prague\", \"Sarajevo\"]", 3),
                createQuestion("History", "Who led the Soviet Union during World War II?", "[\"Vladimir Lenin\", \"Joseph Stalin\", \"Nikita Khrushchev\", \"Mikhail Gorbachev\"]", 1),
                createQuestion("History", "The Battle of Waterloo in 1815 marked the final defeat of which leader?", "[\"Napoleon Bonaparte\", \"Julius Caesar\", \"Alexander the Great\", \"Charlemagne\"]", 0),
                createQuestion("History", "What was the primary cause of the Cold War?", "[\"Economic competition\", \"Ideological conflict\", \"Territorial disputes\", \"Religious differences\"]", 1),

                // Technology Questions
                createQuestion("Technology", "What does CPU stand for?", "[\"Central Processing Unit\", \"Computer Personal Unit\", \"Central Power Unit\", \"Core Processing Unit\"]", 0),
                createQuestion("Technology", "Who is the founder of Microsoft?", "[\"Steve Jobs\", \"Jeff Bezos\", \"Bill Gates\", \"Elon Musk\"]", 2),
                createQuestion("Technology", "What does \"HTTP\" stand for?", "[\"HyperText Transfer Protocol\", \"High-Tech Text Protocol\", \"Hyper-Transfer Text Protocol\", \"Hyper-Text Transmission Protocol\"]", 0),
                createQuestion("Technology", "What company developed the Java programming language?", "[\"Microsoft\", \"Apple\", \"Sun Microsystems\", \"IBM\"]", 2),
                createQuestion("Technology", "In what year was the first iPhone released?", "[\"2005\", \"2007\", \"2008\", \"2010\"]", 1),
                createQuestion("Technology", "What does \"URL\" stand for?", "[\"Uniform Resource Locator\", \"Universal Resource Link\", \"Uniform Resource Link\", \"Universal Resource Locator\"]", 0),
                createQuestion("Technology", "Which company owns the Android operating system?", "[\"Apple\", \"Microsoft\", \"Samsung\", \"Google\"]", 3),
                createQuestion("Technology", "What is the main function of a router?", "[\"To connect to the internet\", \"To direct traffic between networks\", \"To store files\", \"To run applications\"]", 1),
                createQuestion("Technology", "What does \"RAM\" stand for?", "[\"Read-Only Memory\", \"Random-Access Memory\", \"Rapid-Action Memory\", \"Run-And-Manage Memory\"]", 1),
                createQuestion("Technology", "Who is considered the father of the World Wide Web?", "[\"Bill Gates\", \"Steve Jobs\", \"Tim Berners-Lee\", \"Vint Cerf\"]", 2),
                createQuestion("Technology", "What does \"AI\" stand for?", "[\"Automated Intelligence\", \"Artificial Intelligence\", \"Algorithmic Intelligence\", \"Advanced Intelligence\"]", 1),
                createQuestion("Technology", "Which social media platform was founded by Mark Zuckerberg?", "[\"Twitter\", \"Instagram\", \"Snapchat\", \"Facebook\"]", 3),
                createQuestion("Technology", "What does \"VPN\" stand for?", "[\"Virtual Private Network\", \"Very Private Network\", \"Virtual Public Network\", \"Verified Private Network\"]", 0),
                createQuestion("Technology", "What is the name of the first electronic general-purpose computer?", "[\"ENIAC\", \"UNIVAC\", \"COLOSSUS\", \"EDVAC\"]", 0),
                createQuestion("Technology", "What does \"SSD\" stand for in the context of computer storage?", "[\"Solid State Drive\", \"Secure Storage Device\", \"Software Storage Drive\", \"System Storage Device\"]", 0),
                createQuestion("Technology", "Which programming language is commonly used for developing iOS apps?", "[\"Java\", \"Kotlin\", \"Swift\", \"Python\"]", 2),
                createQuestion("Technology", "What is \"the cloud\" in cloud computing?", "[\"A physical server in your home\", \"A network of servers hosted on the Internet\", \"A type of data storage device\", \"A new operating system\"]", 1),
                createQuestion("Technology", "What does HTML stand for?", "[\"Hyper Text Markup Language\", \"High Tech Modern Language\", \"Hyperlink and Text Markup Language\", \"Home Tool Markup Language\"]", 0),
                createQuestion("Technology", "Which company created the popular video game \"Fortnite\"?", "[\"EA Sports\", \"Epic Games\", \"Activision\", \"Nintendo\"]", 1),
                createQuestion("Technology", "What is a \"firewall\" in computer security?", "[\"A physical barrier to prevent fire\", \"A software that monitors network traffic\", \"A type of antivirus\", \"A hardware cooling system\"]", 1),

                // Geography Questions
                createQuestion("Geography", "What is the capital of Japan?", "[\"Beijing\", \"Seoul\", \"Tokyo\", \"Bangkok\"]", 2),
                createQuestion("Geography", "Which is the longest river in the world?", "[\"Amazon\", \"Nile\", \"Yangtze\", \"Mississippi\"]", 1),
                createQuestion("Geography", "What is the largest desert in the world?", "[\"Sahara\", \"Gobi\", \"Arabian\", \"Antarctic Polar Desert\"]", 3),
                createQuestion("Geography", "Which country is known as the Land of the Rising Sun?", "[\"China\", \"South Korea\", \"Japan\", \"Thailand\"]", 2),
                createQuestion("Geography", "What is the highest mountain in the world?", "[\"K2\", \"Kangchenjunga\", \"Mount Everest\", \"Lhotse\"]", 2),
                createQuestion("Geography", "Which ocean is the largest?", "[\"Atlantic\", \"Indian\", \"Arctic\", \"Pacific\"]", 3),
                createQuestion("Geography", "What is the capital of Canada?", "[\"Toronto\", \"Vancouver\", \"Montreal\", \"Ottawa\"]", 3),
                createQuestion("Geography", "Which continent is the most populous?", "[\"Africa\", \"Europe\", \"Asia\", \"North America\"]", 2),
                createQuestion("Geography", "In which country would you find the Eiffel Tower?", "[\"Italy\", \"Spain\", \"France\", \"Germany\"]", 2),
                createQuestion("Geography", "What is the capital of Australia?", "[\"Sydney\", \"Melbourne\", \"Canberra\", \"Brisbane\"]", 2),
                createQuestion("Geography", "Which country has the most natural lakes?", "[\"USA\", \"Canada\", \"Russia\", \"Finland\"]", 1),
                createQuestion("Geography", "Which U.S. state is known as the Sunshine State?", "[\"California\", \"Florida\", \"Arizona\", \"Texas\"]", 1),
                createQuestion("Geography", "What is the smallest country in the world?", "[\"Monaco\", \"Nauru\", \"Tuvalu\", \"Vatican City\"]", 3),
                createQuestion("Geography", "The Great Barrier Reef is located off the coast of which country?", "[\"Australia\", \"Brazil\", \"Mexico\", \"Indonesia\"]", 0),
                createQuestion("Geography", "What is the capital of Russia?", "[\"Saint Petersburg\", \"Moscow\", \"Kazan\", \"Novosibirsk\"]", 1),
                createQuestion("Geography", "Which river flows through Paris?", "[\"Thames\", \"Danube\", \"Seine\", \"Rhine\"]", 2),
                createQuestion("Geography", "Which country is also a continent?", "[\"India\", \"Greenland\", \"Australia\", \"Brazil\"]", 2),
                createQuestion("Geography", "Mount Fuji is the highest point in which country?", "[\"China\", \"South Korea\", \"Japan\", \"Vietnam\"]", 2),
                createQuestion("Geography", "Which country is home to the ancient city of Machu Picchu?", "[\"Chile\", \"Bolivia\", \"Peru\", \"Ecuador\"]", 2),
                createQuestion("Geography", "What is the capital of the United Kingdom?", "[\"Manchester\", \"Birmingham\", \"Edinburgh\", \"London\"]", 3)
            );

            questionRepository.saveAll(questions);
            System.out.println("Seeded " + questions.size() + " questions to the database.");
        } else {
            System.out.println("Database already contains questions. Skipping seed.");
        }
    }

    private Question createQuestion(String category, String text, String options, int correctIndex) {
        Question q = new Question();
        q.setCategory(category);
        q.setText(text);
        q.setOptions(options);
        q.setCorrectIndex(correctIndex);
        return q;
    }
}
