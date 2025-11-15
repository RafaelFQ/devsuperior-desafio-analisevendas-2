package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String arquivo = sc.nextLine();

        List<Sale> lista = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                lista.add(new Sale(month, year, seller, items, total ));
                line = br.readLine();
            }

            System.out.println();
            System.out.println("Total de vendas por vendedor:");

            Map<String, Double> totalPorVendedor = lista.stream()
                    .collect(Collectors.groupingBy(
                            Sale::getSeller,
                            Collectors.summingDouble(Sale::getTotal)
                    ));

            // Exibe cada vendedor com o total formatado
            totalPorVendedor.forEach((seller, total) ->
                    System.out.printf("%s - R$ %.2f%n", seller, total)
            );

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        sc.close();
    }

}
