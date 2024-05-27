
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;

public class trabalhador {

    private String nome, cpf, cep;
    private double SalarioBruto, inss, irrf, SalarioLiquido;
    private int NrDeDependentes;
    SalarioLiquido  = SalarioBruto - inss - irrf;

    public trabalhador(String nome, String cpf, String cep, double SalarioBruto, int NrDeDependentes) {
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.SalarioBruto = SalarioBruto;
        this.NrDeDependentes = NrDeDependentes;
        calculoinss();
        calculoirrf();
        consultaCEP();
    }

    private void calculoinss() {
        if (SalarioBruto <= 1412.00) {
            inss = 0.075;
        } else if (SalarioBruto >= 1412.01 && SalarioBruto <= 2666.68) {
            inss = 0.09;
        } else if (SalarioBruto >= 2666.69 && SalarioBruto <= 4000.03) {
            inss = 0.12;
        } else if (SalarioBruto >= 4000.04 && SalarioBruto <= 7786.02) {
            inss = 0.14;
        }
    }

    private void calculoirrf() {
        if (SalarioBruto <= 2259.20) {
            irrf = (SalarioBruto);
        } else if (SalarioBruto >= 2259.21 && SalarioBruto <= 2826.65) {
            irrf = (SalarioBruto * 0.075) - 169.44;
        } else if (SalarioBruto >= 2826.66 && SalarioBruto <= 3751.05) {
            irrf = (SalarioBruto * 0.15) - 381.44;
        } else if (SalarioBruto >= 3751.06 && SalarioBruto <= 4664.68) {
            irrf = (SalarioBruto * 0.225) - 662.77;
        } else if (SalarioBruto >= 4664.69) {
            irrf = (SalarioBruto * 0.275) - 896.00;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public double getSalarioBruto() {
        return SalarioBruto;
    }

    public void setSalarioBruto(double SalarioBruto) {
        this.SalarioBruto = SalarioBruto;
        calculoinss();
    }

    public int getNrDeDependentes() {
        return NrDeDependentes;
    }

    public void setNrDeDependentes(int NrDeDependentes) {
        this.NrDeDependentes = NrDeDependentes;
    }

    public double getInss() {
        return inss;
    }

    public void setInss(double inss) {
        this.inss = inss;
    }

    public double getIrrf() {
        return irrf;
    }

    public void setIrrf(double irrf) {
        this.irrf = irrf;
    }

    private void consultaCEP() {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha na consulta do CEP: Código de erro HTTP " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
            JSONObject jsonResponse = new JSONObject(response.toString());
            enderecoCompleto = jsonResponse.getString("logradouro") + ", "
                    + jsonResponse.getString("bairro") + ", "
                    + jsonResponse.getString("localidade") + " - "
                    + jsonResponse.getString("uf") + ", "
                    + jsonResponse.getString("cep");
        } catch (IOException | RuntimeException e) {
        } 

        

    

    

    

    

    public static boolean isCPFValid(String cpf) {
        if (cpf.length() != 11 || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return false;
        }

        cpf = cpf.replace("-", "");

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = sum % 11 == 0 || sum % 11 == 1 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = sum % 11 == 0 || sum % 11 == 1 ? 0 : 11 - (sum % 11);

        return (cpf.charAt(9) == firstDigit + '0') && (cpf.charAt(10) == secondDigit + '0');
    }

    public trabalhador(String nome, String cpf, String cep, double SalarioBruto, int NrDeDependentes) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trabalhador.txt", true))) {
            writer.write(nome + "," + cpf + "," + cep + "," + SalarioBruto + "," + NrDeDependentes + "," + inss + "," + irrf + "," + SalarioLiquido + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
